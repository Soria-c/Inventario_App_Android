import { Request } from 'express';
import { TypesService } from './types.service';
export declare class TypesController {
    private readonly typesService;
    constructor(typesService: TypesService);
    getAllFromTypeByName(param: object): {
        msg: any;
    };
    createType(req: Request): any;
    deleteTypeByName(type: object): {
        msg: string;
    };
    modifyTypeName(param: string, req: Request): {
        msg: string;
    };
}
