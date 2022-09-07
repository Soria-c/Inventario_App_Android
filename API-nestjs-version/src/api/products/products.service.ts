import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Global } from './entity/global.entity';

@Injectable()
export class ProductsService {

  constructor(
    @InjectRepository(Global)
    private globalRepository: Repository<Global>
  ){}
  getAll() {
    return this.globalRepository.find({relations: ['tproducts', 'tproducts.items']})
  }

  // deleteAllProducts() {
  //   return { msg: 'All products have been deleted'}
  // }
}
