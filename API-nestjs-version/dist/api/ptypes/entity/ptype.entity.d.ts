import { Item } from "../../items/entity/item.entity";
export declare class Type {
    name: string;
    total_buy_price: number;
    total_sell_price: number;
    total_earning: number;
    stock: number;
    items: Item[];
    global_id: number;
}
