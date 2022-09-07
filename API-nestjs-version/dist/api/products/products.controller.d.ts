import { ProductsService } from './products.service';
export declare class ProductsController {
    private readonly productsService;
    constructor(productsService: ProductsService);
    all(): Promise<import("./entity/global.entity").Global[]>;
}
