import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {NewBookComponent} from "./new-book/new-book.component";

const routes: Routes = [

  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: 'books', component: HomeComponent},
  { path: 'books/new', component: NewBookComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
