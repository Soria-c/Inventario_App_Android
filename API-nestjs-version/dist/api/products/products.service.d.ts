import { Repository } from 'typeorm';
import { Global } from './entity/global.entity';
export declare class ProductsService {
    private globalRepository;
    constructor(globalRepository: Repository<Global>);
    getAll(): Promise<Global[]>;
}
