import {Component, OnInit} from '@angular/core';
import {BookService} from "../service/book.service";
import {Book} from "../book/book.model";
import {Router} from "@angular/router";
import {NotificationService} from "../service/notification.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  books: Book[] = [];

  subscription: Subscription | undefined;

  constructor(private bookService: BookService, private router: Router, private notificationService: NotificationService) {

    this.books = [];
  }

  ngOnInit(): void {
    this.bookService.getBooks().subscribe(
      (response) => {
        console.log(response);
        // @ts-ignore
        this.books = response.books;
        this.notificationService.onSuccess(response.message);
      },
      (error: any) => this.notificationService.onError(error.message),
      () => console.log('Done getting users')
    );
    this.subscription=this.bookService.booksChanged.subscribe(
      (books: Book[]) => {
        this.books = books;
      }
    )
  }


  onClick() {
    // @ts-ignore
    this.router.navigate(['/books/new']);
  }

}
