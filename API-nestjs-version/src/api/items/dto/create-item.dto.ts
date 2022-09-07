import { IsAlpha, IsInt, IsNotEmpty, IsNumber } from "class-validator";

export class CreateItemDTO {

    @IsAlpha()
    @IsNotEmpty()
    name: string;
    
    @IsNumber()
    @IsNotEmpty()
    sell_price: number;

    @IsNumber()
    @IsNotEmpty()
    buy_price: number;

    @IsInt()
    @IsNotEmpty()
    stock: number;

}