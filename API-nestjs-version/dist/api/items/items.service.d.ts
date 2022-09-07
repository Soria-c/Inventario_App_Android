import { Repository } from 'typeorm';
import { CreateItemDTO } from './dto/create-item.dto';
import { Item } from './entity/item.entity';
export declare class ItemsService {
    private itemsRepository;
    constructor(itemsRepository: Repository<Item>);
    createItem(type: string, body: CreateItemDTO): Promise<{
        type_name: string;
        name: string;
        sell_price: number;
        buy_price: number;
        stock: number;
    } & Item>;
    deleteItemByName(params: any): Promise<import("typeorm").DeleteResult>;
    getItemByName(params: any): Promise<any>;
    updateItemBYName(params: any, body: any): Promise<any>;
}
