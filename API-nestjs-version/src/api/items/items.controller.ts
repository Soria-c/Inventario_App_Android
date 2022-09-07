import { Body, Controller, Delete, Get, HttpCode, Param, Patch, Post, Req, Res } from '@nestjs/common';
import { Request, Response } from 'express';
import { CreateItemDTO } from './dto/create-item.dto';
import { UpdateItemDTO } from './dto/update-item.dto';
import { ItemsService } from './items.service';

@Controller('api/products/types/:type')
export class ItemsController {
  constructor(private readonly itemService: ItemsService) {}

  @Post()
  createItem(@Param('type') param: string, @Body() itemDTO:CreateItemDTO) {
    return this.itemService.createItem(param, itemDTO);
  }

  @Delete(':item')
  deleteItemByName(@Param() params: any) {
    return this.itemService.deleteItemByName(params);
  }

  @Get(':item')
  async getItemByName(@Param() params: any, @Res({ passthrough: true }) response:Response) {
    const re = await this.itemService.getItemByName(params);
    if (re){
       return re;
    }
    response.status(404).json({'msg':"Not found"})
  }

  @Patch(':item')
  async updateItemByName(@Param() params: any,  @Body() itemDTO:UpdateItemDTO, @Res({ passthrough: true }) response:Response) {
      const re = await this.itemService.updateItemBYName(params, itemDTO);
      if (re){
        return re;
     }
     response.status(404).json({'msg':"Not found"})
  }
}
