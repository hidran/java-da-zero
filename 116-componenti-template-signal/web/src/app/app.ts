import { Component } from '@angular/core';
import { CatalogPage } from './catalog/catalog-page/catalog-page';

@Component({
  selector: 'app-root',
  imports: [CatalogPage],
  templateUrl: './app.html',
})
export class App {}
