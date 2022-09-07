import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common';
import { TypeDTO } from './dto/type.dto';
import { TypesService } from './types.service';

@Controller('api/products/types')
export class TypesController {
  constructor(private readonly typesService: TypesService) {}

  @Get(':type')
  getAllFromTypeByName(@Param('type') param: object) {
    return this.typesService.getAllFromTypeByName(param);
  }

  @Post()
  createType(@Body() typeDTO: TypeDTO) {
    return this.typesService.createType(typeDTO);
  }

  @Delete(':type')
  deleteTypeByName(@Param('type') type: object) {
    return this.typesService.deleteTypeByName(type);
  }

  @Patch(':type')
  modifyTypeName(@Param('type') param: string, @Body() typeDTO: TypeDTO) {
    return this.typesService.updateTypeName(param, typeDTO);
  }
}
