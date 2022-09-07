import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Type } from "../../ptypes/entity/ptype.entity";

@Entity('global')
export class Global {

    @PrimaryGeneratedColumn()
    id:number;

    @Column()
	global_buy_price:number; 

    @Column()
	global_sell_price:number; 

    @Column()
	global_stock:number; 

    @Column()
	global_earning:number;

    @OneToMany(type => Type, types => types.global_id)
    tproducts: Type[]

}