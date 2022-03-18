import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../service/book.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {NotificationService} from "../service/notification.service";
import {Book} from "../book/book.model";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.css']
})
export class NewBookComponent implements OnInit {

  // @ts-ignore
  bookForm: FormGroup;
  id: number = 0;
  editMode = true;
  book: Book | undefined;
  subscription: Subscription | undefined;

  constructor(private bookService: BookService,
              private router: Router,
              private notificationService: NotificationService,
              private route: ActivatedRoute) {
    this.initForm();


  }

  ngOnInit(): void {
    this.route.params.subscribe((
      params: Params
    ) => {
      this.id = +params['id'];
      this.editMode = params['id'] != null;

      // @ts-ignore

    });


   if(this.editMode){
     this.bookService.getBook(this.id).subscribe(
       (response) => {
         // @ts-ignore
         this.book = response.books[0];
         this.notificationService.onSuccess(response.message);
       },
       (error: any) => this.notificationService.onError(error.reason),
       () => {
         console.log(this.book);
         console.log("done getting book");
         this.initForm();

       });
   }

  }

  private initForm() {

    let title = "";
    let author = "";
    let genre = "";
    let year = 0;


    if (this.editMode) {

      // @ts-ignore
      title = this.book?.title;
      // @ts-ignore
      author = this.book?.author;
      // @ts-ignore
      genre = this.book?.genre;
      // @ts-ignore
      year = this.book?.year

    }
    this.bookForm = new FormGroup({
      'title': new FormControl(title, Validators.required),
      'author': new FormControl(author, Validators.required),
      'genre': new FormControl(genre, Validators.required),
      'year': new FormControl(year, [Validators.required, Validators.pattern(/^[1-9]+[0-9]*$/)])
    })

  }

  onCancel() {
    this.router.navigate(['/']);
  }


  validFormCheck(){
    for (let e in this.bookForm.value) {
      if (this.bookForm.value[e] == null) {
        this.notificationService.onError(e + " is required ");
      }
    }
    if (this.bookForm.value['year'] != null && this.bookForm.value['year'].match(/^[1-9]+[0-9]*$/) == null) {
      this.notificationService.onError("year must be a number")
    }

  }

  onCreate() {
    if (this.bookForm.valid == true) {
      this.bookService.addBook(this.bookForm.value).subscribe(
        (response) => {
          console.log(response);
          // @ts-ignore
          this.notificationService.onSuccess(response.message);
        },
        (error: any) => this.notificationService.onError(error.message),
        () => console.log('Done adding book')
      );
      this.router.navigate(['/']);
    } else {


      for (let e in this.bookForm.value) {
        if (this.bookForm.value[e] == null) {
          this.notificationService.onError(e + " is required ");
        }
      }
      if (this.bookForm.value['year'] != null && this.bookForm.value['year'].match(/^[1-9]+[0-9]*$/) == null) {
        this.notificationService.onError("year must be a number")
      }

    }
  }

  onUpdate(){
      if (this.bookForm.valid == true) {
        this.bookService.updateBook(this.bookForm.value,this.id).subscribe(
          (response) => {
            console.log(response);
            // @ts-ignore
            this.notificationService.onSuccess(response.message);
          },
          (error: any) => this.notificationService.onError(error.message),
          () => console.log('Done updating book')
        );
        this.router.navigate(['/']);
      } else {
        for (let e in this.bookForm.value) {
          if (this.bookForm.value[e] == "") {
            this.notificationService.onError(e + " is required ");
          }
        }
        if (this.bookForm.value['year'] != "" && this.bookForm.value['year'].match(/^[1-9]+[0-9]*$/) == null) {
          this.notificationService.onError("year must be a number")
        }

      }
  }
}
