import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Global } from './entity/global.entity';
import { ProductsController } from './products.controller';
import { ProductsService } from './products.service';

@Module({
  imports: [TypeOrmModule.forFeature([Global])],
  controllers: [ProductsController],
  providers: [ProductsService],
})
export class ProductsModule {}
