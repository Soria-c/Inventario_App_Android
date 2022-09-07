import { Repository } from 'typeorm';
import { TypeDTO } from './dto/type.dto';
import { Type } from './entity/ptype.entity';
export declare class TypesService {
    private typeRepository;
    constructor(typeRepository: Repository<Type>);
    getAllFromTypeByName(name: any): Promise<Type>;
    createType(body: TypeDTO): Promise<TypeDTO & Type>;
    deleteTypeByName(name: any): Promise<import("typeorm").DeleteResult>;
    updateTypeName(name: string, body: TypeDTO): Promise<import("typeorm").UpdateResult>;
}
