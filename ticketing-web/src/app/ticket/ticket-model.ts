export class Ticket {
  constructor(
    public title: string,
    public omschrijving: string,
    public deadline: string,
    public assignedUser: number,
    public project: number,
    public group: number,
    public priority: string
  ) {}
}
