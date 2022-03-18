import { Injectable } from '@angular/core';
import {Book} from "../book/book.model";
import {catchError, map, Observable, Subject, tap, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CustomHttpResponse} from "../interface/custom-http-response";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private server = environment.apiUrl+"/api/v1";

  booksChanged= new Subject<Book[]>();

  private books:Book[]=[];

  constructor(private http:HttpClient) { }


  getBooks():Observable<CustomHttpResponse>{
    return this.http.get<CustomHttpResponse>(this.server+"/books").pipe(

        tap(response=>{

           // @ts-ignore
          this.books=[...response.books];
          console.log(this.books);
        }),

        catchError(this.handleError)
    )
  }
  addBook(book:Book):Observable<Book>{
    this.books.push(book);
    this.booksChanged.next(this.books.slice());
    return this.http.post<Book>(this.server+"/books/add",book);
  }

  getBook(bookId:number):Observable<CustomHttpResponse>{
    return this.http.get<CustomHttpResponse>(`${this.server}/books/${bookId}`).pipe(
      catchError(this.handleError)
    )
    ;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
      errorMessage = `A client error occurred - ${error.error.message}`;
    } else {
      if (error.error.reason) {
        errorMessage = `${error.error.reason} - Error code ${error.status}`;
      } else {
        errorMessage = `An error occurred - Error code ${error.status}`;
      }
    }
    return throwError(errorMessage);
  }




}
