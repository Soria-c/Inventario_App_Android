import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { TypeDTO } from './dto/type.dto';
import { Type } from './entity/ptype.entity';

@Injectable()
export class TypesService {

    constructor(
        @InjectRepository(Type)
        private typeRepository:Repository<Type>
    ){}

    async getAllFromTypeByName(name:any) {
        const re = await this.typeRepository.findOne({where: {name:name}, relations: ['items']});
        return re;
    }

    async createType(body:TypeDTO){
        const re = await this.typeRepository.save(body);
        return re;
    }

    async deleteTypeByName(name:any) {
        const re = await this.typeRepository.delete(name);
        return re;
    }

    async updateTypeName(name:string, body:TypeDTO) {
        const re = await this.typeRepository.update(name, body);
        return re;
    }
}
