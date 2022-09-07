"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ItemsController = void 0;
const common_1 = require("@nestjs/common");
const create_item_dto_1 = require("./dto/create-item.dto");
const update_item_dto_1 = require("./dto/update-item.dto");
const items_service_1 = require("./items.service");
let ItemsController = class ItemsController {
    constructor(itemService) {
        this.itemService = itemService;
    }
    createItem(param, itemDTO) {
        return this.itemService.createItem(param, itemDTO);
    }
    deleteItemByName(params) {
        return this.itemService.deleteItemByName(params);
    }
    async getItemByName(params, response) {
        const re = await this.itemService.getItemByName(params);
        if (re) {
            return re;
        }
        response.status(404).json({ 'msg': "Not found" });
    }
    async updateItemByName(params, itemDTO, response) {
        const re = await this.itemService.updateItemBYName(params, itemDTO);
        if (re) {
            return re;
        }
        response.status(404).json({ 'msg': "Not found" });
    }
};
__decorate([
    (0, common_1.Post)(),
    __param(0, (0, common_1.Param)('type')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, create_item_dto_1.CreateItemDTO]),
    __metadata("design:returntype", void 0)
], ItemsController.prototype, "createItem", null);
__decorate([
    (0, common_1.Delete)(':item'),
    __param(0, (0, common_1.Param)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], ItemsController.prototype, "deleteItemByName", null);
__decorate([
    (0, common_1.Get)(':item'),
    __param(0, (0, common_1.Param)()),
    __param(1, (0, common_1.Res)({ passthrough: true })),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], ItemsController.prototype, "getItemByName", null);
__decorate([
    (0, common_1.Patch)(':item'),
    __param(0, (0, common_1.Param)()),
    __param(1, (0, common_1.Body)()),
    __param(2, (0, common_1.Res)({ passthrough: true })),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, update_item_dto_1.UpdateItemDTO, Object]),
    __metadata("design:returntype", Promise)
], ItemsController.prototype, "updateItemByName", null);
ItemsController = __decorate([
    (0, common_1.Controller)('api/products/types/:type'),
    __metadata("design:paramtypes", [items_service_1.ItemsService])
], ItemsController);
exports.ItemsController = ItemsController;
//# sourceMappingURL=items.controller.js.map