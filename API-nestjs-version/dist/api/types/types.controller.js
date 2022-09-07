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
exports.TypesController = void 0;
const common_1 = require("@nestjs/common");
const types_service_1 = require("./types.service");
let TypesController = class TypesController {
    constructor(typesService) {
        this.typesService = typesService;
    }
    getAllFromTypeByName(param) {
        return this.typesService.getAllFromTypeByName(param);
    }
    createType(req) {
        return this.typesService.createType(req.body);
    }
    deleteTypeByName(type) {
        return this.typesService.deleteTypeByName(type);
    }
    modifyTypeName(param, req) {
        return this.typesService.updateTypeName(param, req.body);
    }
};
__decorate([
    (0, common_1.Get)(':type'),
    __param(0, (0, common_1.Param)('type')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TypesController.prototype, "getAllFromTypeByName", null);
__decorate([
    (0, common_1.Post)(),
    __param(0, (0, common_1.Req)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TypesController.prototype, "createType", null);
__decorate([
    (0, common_1.Delete)(':type'),
    __param(0, (0, common_1.Param)('type')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TypesController.prototype, "deleteTypeByName", null);
__decorate([
    (0, common_1.Patch)(':type'),
    __param(0, (0, common_1.Param)('type')),
    __param(1, (0, common_1.Req)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], TypesController.prototype, "modifyTypeName", null);
TypesController = __decorate([
    (0, common_1.Controller)('api/products/types'),
    __metadata("design:paramtypes", [types_service_1.TypesService])
], TypesController);
exports.TypesController = TypesController;
//# sourceMappingURL=types.controller.js.map