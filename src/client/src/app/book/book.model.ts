

export  class Book{

  public title:string="";
  public author:string="";
  public genre:string="";
  public year: number=0;


  constructor(title: string, author: string, genre: string, year: number) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.year = year;
  }

}
