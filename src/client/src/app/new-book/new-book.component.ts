import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../service/book.service";
import {Router} from "@angular/router";
import {NotificationService} from "../service/notification.service";
import {Book} from "../book/book.model";


@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.css']
})
export class NewBookComponent implements OnInit {

  // @ts-ignore
  bookForm: FormGroup;


  constructor(private bookService: BookService, private router: Router, private notificationService: NotificationService) {
    this.initForm();
  }


  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.bookForm = new FormGroup({
      'title': new FormControl(null, Validators.required),
      'author': new FormControl(null, Validators.required),
      'genre': new FormControl(null, Validators.required),
      'year': new FormControl(null, [Validators.required,Validators.pattern(/^[1-9]+[0-9]*$/)])
    })

  }

  onCancel() {

    this.router.navigate(['/']);
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
        () => console.log('Done adding user')
      );
      this.router.navigate(['/']);

    }else{
      for(let e in this.bookForm.value){
          if(this.bookForm.value[e]==null) {
            this.notificationService.onError(e + " is required ");
          }
      }

      if(this.bookForm.value['year']!=null&&this.bookForm.value['year'].match(/^[1-9]+[0-9]*$/)==null){
        this.notificationService.onError("year must be a number")
      }

    }
  }



}
