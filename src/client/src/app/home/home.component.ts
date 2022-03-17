import {Component, OnInit} from '@angular/core';
import {BookService} from "../book/book.service";
import {Book} from "../book/book.model";
import {Router} from "@angular/router";
import {NotificationService} from "../service/notification.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  books: Book[] = [];

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
  }


  onClick() {
    // @ts-ignore
    this.router.navigate(['/books/new']);
  }

}
