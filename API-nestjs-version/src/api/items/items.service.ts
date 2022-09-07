import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateItemDTO } from './dto/create-item.dto';
import { Item } from './entity/item.entity';

@Injectable()
export class ItemsService {
  constructor(
    @InjectRepository(Item)
    private itemsRepository: Repository<Item>,
  ) {} 

  createItem(type: string, body: CreateItemDTO) {
    return this.itemsRepository.save({ ...body, type_name: type });
  }

  deleteItemByName(params: any) {
    return this.itemsRepository.delete({ name: params.item, type_name: params.type });
  }

  async getItemByName(params: any) {
    let re;
    console.log();
    
    if (
      (re = await this.itemsRepository.find({
        where: { name: params.item, type_name: params.type },
      })).length
    ) {

      return re;
    }

    return null;
  }

  async updateItemBYName(params: any, body: any) {
    
    console.log(params);
    console.log(body);
    
    
    let re;
    
    re = await this.itemsRepository.update({name: params.item, type_name: params.type},body);
    return re;
    
  }
}
