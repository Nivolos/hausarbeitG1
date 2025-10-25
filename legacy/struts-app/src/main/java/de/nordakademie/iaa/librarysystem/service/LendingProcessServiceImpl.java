package de.nordakademie.iaa.librarysystem.service;

import com.opensymphony.xwork2.inject.Inject;
import de.nordakademie.iaa.librarysystem.dao.LendingProcessDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.model.Reminder;
import de.nordakademie.iaa.librarysystem.service.exception.*;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ExtendPublicationNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.LostPublicationNotPossibleException;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.RemindPublicationReturnNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ReturnNotPossibleException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.mail.Message.RecipientType;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Die Klasse LendingProcessServiceImpl ist der Kommunikator zwischen der LendingProcessAction und der LibraryUserProcessDao.
 * Sie ermöglicht das Ausleihen und zurückgeben von Publikationen für einen Ausleiher.
 * Zusätzlich ermöglicht sie auch das abmahnen und verlängern einer Ausleihe.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Service
public class LendingProcessServiceImpl implements LendingProcessService  {
    private final LendingProcessDAO lendingProcessDao;

    private final PublicationService publicationService;

    private ReminderService reminderService;
    private LibrarySystemSettingService librarySystemSettingService;

    private final Integer maxReminderCount;
    private final Integer lendingIntervalInDays;
    private final Integer reminderIntervalInDays;
    private final Integer maxExtensionCount;

    @Inject
    public LendingProcessServiceImpl(LendingProcessDAO lendingProcessDao,
                                     PublicationService publicationService,
                                     ReminderService reminderService,
                                     LibrarySystemSettingService librarySystemSettingService) {
        this.lendingProcessDao = lendingProcessDao;
        this.publicationService = publicationService;
        this.reminderService = reminderService;
        this.librarySystemSettingService = librarySystemSettingService;

        maxExtensionCount = Integer.parseInt(librarySystemSettingService
                .readSystemSetting("MAX_EXTENSION_COUNT").getParameterValue());

        maxReminderCount = Integer.parseInt(
                librarySystemSettingService.readSystemSetting("MAX_REMINDER_COUNT").getParameterValue()
        );

        lendingIntervalInDays = Integer.parseInt(librarySystemSettingService
                .readSystemSetting("LENDING_INTERVAL_IN_DAYS").getParameterValue());

        reminderIntervalInDays = Integer.parseInt(librarySystemSettingService
                .readSystemSetting("REMINDER_INTERVAL_IN_DAYS").getParameterValue());
    }

    private LendingProcess setLendingProcessDates(LendingProcess lendingProcess) {
        /* Set lending date to today */
        Date today = Calendar.getInstance().getTime();
        lendingProcess.setLendingDate(today);
        /* Set planned return date to today + lending period set by system settings */
        Calendar lendingEndCalendar = Calendar.getInstance();
        lendingEndCalendar.add(Calendar.DATE, lendingIntervalInDays);
        /* Set to next day */
        Date plannedReturnNextDay = DateUtils.addDays(lendingEndCalendar.getTime(), 1);
        lendingProcess.setPlannedReturnDate(plannedReturnNextDay);
        return lendingProcess;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void lendPublication(LendingProcess lendingProcess)
            throws ServiceException, PublicationNotAvailableException {
        if (lendingProcess.getId() != null) {
            throw new ServiceException("The lending process cannot be created " +
                    "because a publication type with the given ID already exists.");
        }
        lendingProcess = setLendingProcessDates(lendingProcess);

        /* Verify at time of request that publication is still available within transaction bound */
        try{
            Publication publication = publicationService.readPublication(lendingProcess.getPublication().getId());
            if(publication == null || publication.getAvailableStock() < 1){
                throw new PublicationNotAvailableException();
            }
            /* Decrease connected publication's available stock count by 1 as publication is lended */
            publication.setAvailableStock(lendingProcess.getPublication().getAvailableStock() - 1);
            try {
                publicationService.updatePublication(publication);
                lendingProcessDao.create(lendingProcess);
            } catch (ServiceException | PersistException e){
                throw new ServiceException(e);
            }
            catch (Constants.ConstantException e) {
                throw new PublicationNotAvailableException();
            }
        }catch(ModelNotFoundException e){
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional
    public void returnPublication(@NotNull LendingProcess lendingProcess) throws ReturnNotPossibleException {
        /* Verify publication has not been returned yet */
        if(lendingProcess.getReturned()){
            throw new ReturnNotPossibleException();
        }

        /* Verify at time of request that publication is still known in database */
        Publication publication = publicationService.readPublication(lendingProcess.getPublication().getId());
        if(publication == null){
            throw new ReturnNotPossibleException();
        }

        lendingProcess.setActualReturnDate(Calendar.getInstance().getTime());
        lendingProcess.setReturned(true);

        /* Increase connected publication's available stock by 1 as publication is returned */
        publication.setAvailableStock(lendingProcess.getPublication().getAvailableStock() + 1);

        /* If publication has been marked as lost, reverse lost status */
        if(lendingProcess.getLost()){
            lendingProcess.setLost(false);
            publication.setTotalStock(publication.getTotalStock() + 1);
        }

        try {
            publicationService.updatePublication(publication);
            lendingProcessDao.update(lendingProcess);
        } catch (ServiceException e){
            throw new ServiceException(e);
        }
        catch (Constants.ConstantException | PersistException e){
            throw new ReturnNotPossibleException();
        }
    }

    @Override
    @Transactional
    public void remindPublicationReturn(LendingProcess lendingProcess) throws RemindPublicationReturnNotPossible {
        /* Verify at time of request that lending process is still known in database */
        Optional<LendingProcess> lendingProcessOptional = lendingProcessDao.load(lendingProcess.getId());
        if(lendingProcessOptional.isEmpty()){
            throw new RemindPublicationReturnNotPossible();
        }
        lendingProcess = lendingProcessOptional.get();

        /* Verify publication is not returned yet,
         * and reminder count is below 3,
         * and publication is not marked as lost,
         * and planned return date (day) is already overdue (excl. time of day part of timestamp)
         * else throw error */
        Integer existingCount = reminderService.findAllRemindersByLendingProcessId(lendingProcess.getId()).size();

        if(lendingProcess.getReturned()
                || lendingProcess.getLost()
                || Calendar.getInstance().getTime().compareTo(lendingProcess.getPlannedReturnDate()) < 0
                || existingCount >= maxReminderCount
        ){
            throw new RemindPublicationReturnNotPossible();
        }

        try {
            Reminder reminder = new Reminder();
            reminder.setLendingProcess(lendingProcess);
            reminder.setDate(Calendar.getInstance().getTime());
            reminderService.createReminder(reminder);

            List<Reminder> reminders = lendingProcess.getReminders();
            reminders.add(reminder);
            lendingProcess.setReminders(reminders);

            lendingProcessDao.update(lendingProcess);

        } catch (PersistException e) {
            throw new ServiceException(e);
        } catch (Constants.ConstantException e){
            throw new RemindPublicationReturnNotPossible();
        }
    }

    public void deleteInvalidReminders(LendingProcess lendingProcess){
        Date plannedDate = lendingProcess.getPlannedReturnDate();
        List<Reminder> reminders = reminderService.findAllRemindersByLendingProcessId(lendingProcess.getId());

        for(Reminder reminder : reminders){
            if(reminder.getDate().compareTo(plannedDate) < 0){
                try {
                    reminderService.deleteReminder(reminder.getId());
                } catch (ModelUsedException e) {
                    throw new ServiceException("Invalid reminder deletion failed");
                }
            }
        }
    }

    @Override
    @Transactional
    public void extendPublication(LendingProcess lendingProcess) throws ExtendPublicationNotPossible {
        /* Verify at time of request that lending process is still known in database */
        Optional<LendingProcess> lendingProcessOptional = lendingProcessDao.load(lendingProcess.getId());
        if(lendingProcessOptional.isEmpty()){
            throw new ExtendPublicationNotPossible();
        }
        lendingProcess = lendingProcessOptional.get();

        /* Verify extension count is below 2,
         * and not lost,
         * and not given back already,
         * such that publication is extendable,
         * else throw error */
        if(lendingProcess.getExtensionCount() >= maxExtensionCount
                || lendingProcess.getLost()
                || lendingProcess.getReturned()){
            throw new ExtendPublicationNotPossible();
        }

        lendingProcess.setExtensionCount(lendingProcess.getExtensionCount() + 1);

        /* Increase planned return date to current return date plus extension period read from system settings */
        lendingProcess.setPlannedReturnDate(
                new Date(lendingProcess
                        .getPlannedReturnDate()
                        .getTime()
                        + TimeUnit.DAYS.toMillis(lendingIntervalInDays))
        );

        try {
            lendingProcessDao.update(lendingProcess);
            deleteInvalidReminders(lendingProcess);
        } catch (PersistException e) {
            throw new ServiceException(e);
        } catch (Constants.ConstantException e){
            throw new ExtendPublicationNotPossible();
        }
    }

    @Override
    @Transactional
    public void lostPublication(LendingProcess lendingProcess) throws LostPublicationNotPossibleException {
        /* Verify at time of request that lending process is still known in database */
        Optional<LendingProcess> lendingProcessOptional = lendingProcessDao.load(lendingProcess.getId());
        if(lendingProcessOptional.isEmpty()){
            throw new LostPublicationNotPossibleException();
        }
        lendingProcess = lendingProcessOptional.get();

        List<Reminder> reminders = reminderService.findAllRemindersByLendingProcessId(lendingProcess.getId());
        Integer reminderCount = reminders.size();

        /* Verify extension count is below `maxReminderCount`,
         * and publication is not returned yet,
         * such that publication can be marked as lost,
         * else throw error */
        if(reminderCount < maxReminderCount
                || lendingProcess.getReturned()
                || lendingProcess.getLost()){
            throw new LostPublicationNotPossibleException();
        }

        /* Increase publications lost amount by 1
         * and decrease publications total stock by 1 */
        Publication publication = lendingProcess.getPublication();
        publication.setLostAmount(publication.getLostAmount() + 1);
        publication.setTotalStock(publication.getTotalStock() - 1);

        lendingProcess.setLost(true);

        try {
            publicationService.updatePublication(publication);
            lendingProcessDao.update(lendingProcess);
        } catch (ServiceException | PersistException e) {
            throw new ServiceException(e);
        } catch (Constants.ConstantException e){
            throw new LostPublicationNotPossibleException();
        }
    }

    @Override
    public LendingProcess readLendingProcess(Long id) throws ServiceException {
        try{
            return lendingProcessDao.load(id).orElseThrow(() -> new ServiceException(String.format(
                    "A publication type with ID %s does not exist.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public LendingProcess updateLendingProcess(LendingProcess lendingProcess) throws ServiceException {
        if (lendingProcess.getId() == null) {
            throw new ServiceException("The lending process can not be updated, because it was not found.");
        }
        try {
            return lendingProcessDao.update(lendingProcess);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LendingProcess> findAllLendingProcesses() {
        return lendingProcessDao.findAll();
    }

    @Override
    public List<LendingProcess> findCurrentLendingProcessesByPublication(Publication publication) {
        return lendingProcessDao.findCurrentByPublication(publication);
    }

    @Override
    @Transactional
    public List<LendingProcess> searchLendingProcess(LendingProcess lendingProcess,
                                                     Boolean includeOutstandingOverdueReminderOnly) {
        String title = null;
        String firstName = null;
        String lastName = null;
        if(lendingProcess != null){
            title = lendingProcess.getPublication().getTitle();
            firstName = lendingProcess.getLibraryUser().getFirstName();
            lastName = lendingProcess.getLibraryUser().getLastName();
        }
        List<LendingProcess> lendingProcesses = lendingProcessDao.search(lendingProcess,
                title,
                firstName,
                lastName
        );

        /* If checked, only include pending overdue reminders */
        if(includeOutstandingOverdueReminderOnly){

            Date expectedLastReminderDateUpperBound = DateUtils.addDays(new Date(),
                    -1 * reminderIntervalInDays.intValue());

            List<LendingProcess> filteredLendingProcesses = new ArrayList<>();

            for(LendingProcess lp : lendingProcesses){
                lp = readLendingProcess(lp.getId());
                /* Keep lending processes without reminder where planned return date is before upper bound */
                if(lp.getReminders().isEmpty() && lp.getPlannedReturnDate().before(expectedLastReminderDateUpperBound)){
                    filteredLendingProcesses.add(lp);
                } else {
                    if(lp.getLatestReminder().getDate().before(expectedLastReminderDateUpperBound)){
                        filteredLendingProcesses.add(lp);
                    }
                }
            }
            return filteredLendingProcesses;
        }

        return lendingProcesses;
    }

    @Override
    public void deleteLendingProcess(Long id) throws ServiceException {
        try {
            lendingProcessDao.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

    public LibrarySystemSettingService getLibrarySystemSettingService() {
        return librarySystemSettingService;
    }

    public void setLibrarySystemSettingService(LibrarySystemSettingService librarySystemSettingService) {
        this.librarySystemSettingService = librarySystemSettingService;
    }

    @Override
    public List<LendingProcess> findByLibraryUser(Long libraryUserId) {
        return lendingProcessDao.findByLibraryUser(libraryUserId);
    }
    @Override
    public void sendReminderEmail(LendingProcess lendingProcess){
        LibraryUser libraryUser = lendingProcess.getLibraryUser();
        String email = libraryUser.getEmail();
        String emailBody = "See English version below \n" +
                "Hallo %s, \n Ihre Publikation %s mit dem Rückgabedatum %s ist " +
                "abgelaufen. Bitte verlängern oder zurückgeben!";
        emailBody = String.format(emailBody, libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(),
                lendingProcess.getPlannedReturnDate().toString());
        String subject = "Ping Bibliothek Reminder";
        sendEmail(email, emailBody, subject);
    }
    @Override
    public void sendStartLendEmail(LendingProcess lendingProcess){
        LibraryUser libraryUser = lendingProcess.getLibraryUser();
        String email = libraryUser.getEmail();
        String emailBody = "See English version below \n" +
                "Hallo %s, \n Ihre Publikation %s wurde gerade ausgeliehen." +
                "\n Ihr Rückgabedatum lautet %s" +
                "\n Wir freuen uns auch in Zukunft auf Ihre nächste Leihe bei uns!" +
                "\n" +
                "\n" +
                "Hello %s, \n your publication %s was lent." +
                "\n your return date is: %s" +
                "\n We with you good reading!";
        emailBody = String.format(emailBody, libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(), lendingProcess.getPlannedReturnDate().toString()
                , libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(), lendingProcess.getPlannedReturnDate().toString());
        String subject = "Ping Bibliothek Start der Ausleihe";
        sendEmail(email, emailBody, subject);
    }

    @Override
    public void sendReturnEmail(LendingProcess lendingProcess){
        LibraryUser libraryUser = lendingProcess.getLibraryUser();
        String email = libraryUser.getEmail();
        String emailBody = "See English version below \n" +
                "Hallo %s, \n Ihre Publikation %s wurde gerade zurückgegeben." +
                "\n Wir freuen uns auch in Zukunft auf Ihre nächste Leihe bei uns!" +
                "\n" +
                "\n" +
                "Hello %s, \n your publication %s was returned." +
                "\n We hope to read with you in the future again!";
        emailBody = String.format(emailBody,libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(),
                libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle());
        String subject = "Ping Bibliothek Rückabe der Ausleihe";
        sendEmail(email, emailBody, subject);
    }

    @Override
    public void sendExtendEmail(LendingProcess lendingProcess){
        LibraryUser libraryUser = lendingProcess.getLibraryUser();
        String email = libraryUser.getEmail();
        String emailBody = "See English version below \n" +
                "Hallo %s, \n Ihre Publikation %s wurde gerade verlängert." +
                "\n Ihr neues Rückgabedatum lautet: %s" +
                "\n" +
                "\n" +
                "Hello %s, \n your publication %s was extended." +
                "\n your new return date is: %s";
        emailBody = String.format(emailBody,libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(), lendingProcess.getPlannedReturnDate().toString()
                ,libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(), lendingProcess.getPlannedReturnDate().toString());
        String subject = "Ping Bibliothek Rückabe der Ausleihe";
        sendEmail(email, emailBody, subject);
    }

    @Override
    public void sendLostEmail(LendingProcess lendingProcess){
        LibraryUser libraryUser = lendingProcess.getLibraryUser();
        String email = libraryUser.getEmail();
        String emailBody = "See English version below \n" +
                "Hallo %s, \n Ihre Publikation %s wurde bei uns als verloren." +
                "\n Wir freuen uns auch in Zukunft auf Ihre nächste Leihe bei uns!" +
                "\n" +
                "\n" +
                "Hello %s, \n your publication %s was marked as lost." +
                "\n We hope to see you soon!";
        emailBody = String.format(emailBody, libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle(),
                libraryUser.getFirstName() + " " + libraryUser.getLastName(),
                lendingProcess.getPublication().getTitle());
        String subject = "Ping Bibliothek Publikation Verloren";
        sendEmail(email, emailBody, subject);
    }

    private void sendEmail(String email, String emailBody, String subject) {


        if(email.isEmpty()){
            subject = "error: " + subject;
            email = "pinguinbibliothek@gmail.com";
        }

        Properties mailProperties = System.getProperties();
        mailProperties.put("mail.smtp.port", "465");
        mailProperties.put("mail.smtp.ssl.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(mailProperties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pinguinbibliothek@gmail.com", "utnvvtqqjnrvfcsw");
            }
        });
        session.setDebug(true);
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pinguinbibliothek@gmail.com"));
            message.addRecipient(RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(emailBody);
            javax.mail.Transport.send(message);
        } catch (MessagingException e) {
            throw new ServiceException("A reminder mail could not be sent.");
        }
    }
}