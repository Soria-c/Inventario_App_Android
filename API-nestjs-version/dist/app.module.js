"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AppModule = void 0;
const common_1 = require("@nestjs/common");
const app_controller_1 = require("./app.controller");
const app_service_1 = require("./app.service");
const products_module_1 = require("./api/products/products.module");
const types_module_1 = require("./api/ptypes/types.module");
const items_module_1 = require("./api/items/items.module");
const typeorm_1 = require("@nestjs/typeorm");
const item_entity_1 = require("./api/items/entity/item.entity");
const ptype_entity_1 = require("./api/ptypes/entity/ptype.entity");
const global_entity_1 = require("./api/products/entity/global.entity");
let AppModule = class AppModule {
};
AppModule = __decorate([
    (0, common_1.Module)({
        imports: [
            products_module_1.ProductsModule,
            types_module_1.TypesModule,
            items_module_1.ItemsModule,
            typeorm_1.TypeOrmModule.forRoot({
                type: 'mysql',
                host: 'localhost',
                port: 3306,
                username: 'enki',
                password: 'asd',
                database: 'nest_eb',
                entities: [item_entity_1.Item, ptype_entity_1.Type, global_entity_1.Global],
                synchronize: false,
            }),
        ],
        controllers: [app_controller_1.AppController],
        providers: [app_service_1.AppService],
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map