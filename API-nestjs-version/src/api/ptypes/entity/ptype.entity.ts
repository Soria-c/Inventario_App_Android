import { Item } from "../../items/entity/item.entity";
import { Column, Entity, JoinColumn, ManyToOne, OneToMany, PrimaryColumn} from "typeorm";
import { Global } from "../../products/entity/global.entity";

@Entity('tproducts')
export class Type {

    @PrimaryColumn()
    name:string;

    @Column()
    total_buy_price:number;

    @Column()
    total_sell_price:number;

    @Column()
    total_earning:number;

    @Column()
    stock:number;

    @OneToMany(type => Item, items => items.type_name)
    items: Item[];

    @ManyToOne(type => Global, global => global.id)
    @JoinColumn({ name: "global_id" })
    global_id: number
}