import { TypeDTO } from './dto/type.dto';
import { TypesService } from './types.service';
export declare class TypesController {
    private readonly typesService;
    constructor(typesService: TypesService);
    getAllFromTypeByName(param: object): Promise<import("./entity/ptype.entity").Type>;
    createType(typeDTO: TypeDTO): Promise<TypeDTO & import("./entity/ptype.entity").Type>;
    deleteTypeByName(type: object): Promise<import("typeorm").DeleteResult>;
    modifyTypeName(param: string, typeDTO: TypeDTO): Promise<import("typeorm").UpdateResult>;
}
