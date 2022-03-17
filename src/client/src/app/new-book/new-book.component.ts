import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../book/book.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.css']
})
export class NewBookComponent implements OnInit {

  // @ts-ignore
  bookForm: FormGroup;

  constructor(private bookService: BookService,private router: Router) {
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
      'year': new FormControl(null, Validators.required),
    })

  }

  onCancel() {

    this.router.navigate(['/']);
  }

  onCreate(){
    this.bookService.addBook(this.bookForm.value);
    this.router.navigate(['/']);
  }
}
