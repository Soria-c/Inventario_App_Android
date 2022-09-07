import { Response } from 'express';
import { CreateItemDTO } from './dto/create-item.dto';
import { UpdateItemDTO } from './dto/update-item.dto';
import { ItemsService } from './items.service';
export declare class ItemsController {
    private readonly itemService;
    constructor(itemService: ItemsService);
    createItem(param: string, itemDTO: CreateItemDTO): Promise<{
        type_name: string;
        name: string;
        sell_price: number;
        buy_price: number;
        stock: number;
    } & import("./entity/item.entity").Item>;
    deleteItemByName(params: any): Promise<import("typeorm").DeleteResult>;
    getItemByName(params: any, response: Response): Promise<any>;
    updateItemByName(params: any, itemDTO: UpdateItemDTO, response: Response): Promise<any>;
}
