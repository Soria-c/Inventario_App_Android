import { Controller, Delete, Get } from '@nestjs/common';
import { ProductsService } from './products.service';

@Controller('api/products')
export class ProductsController {
  constructor(private readonly productsService: ProductsService) {}

  @Get()
  all() {
    return this.productsService.getAll();
  }

  // @Delete()
  // deleteAll() {
  //   return this.productsService.deleteAllProducts();
  // }

  
}
