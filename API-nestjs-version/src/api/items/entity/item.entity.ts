
import { Type } from "../../ptypes/entity/ptype.entity";
import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn } from "typeorm";

@Entity('items')
export class Item {

    @PrimaryGeneratedColumn()
    id:number;

    @Column()
    name:string;

    @Column()
    buy_price:number;

    @Column()
    sell_price:number;

    @Column()
    stock:number;      

    @Column()
    total_buy_price:number; 

    @Column()
    total_sell_price:number;

    @Column()
    total_earning:number;

    @Column()
    unit_earning:number;

    @ManyToOne(type => Type, ptype => ptype.name)
    @JoinColumn({ name: "type_name" })
    @Column()
    type_name:string;
}