import { Optional } from "@nestjs/common";
import { IsAlpha, IsInt, IsNotEmpty, IsNumber, IsOptional } from "class-validator";

export class UpdateItemDTO {

    @IsOptional()
    @IsAlpha()
    name: string;
    
    @IsOptional()
    @IsNumber()
    sell_price: number;

    @IsOptional()
    @IsNumber()
    buy_price: number;

    @IsOptional()
    @IsInt()
    stock: number;
}