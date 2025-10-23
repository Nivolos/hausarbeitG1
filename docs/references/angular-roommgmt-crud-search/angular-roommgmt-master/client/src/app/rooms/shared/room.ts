export class Room {

    constructor(public building?: String,
                public roomNumber?: number,
                public seats?: number,
                public projectorPresent: boolean = false) {
    }

    equals(other: Room): boolean {
        if (!other) {
            return false;
        }
        return this.building === other.building && this.roomNumber === other.roomNumber;
    }
}
