import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ProductsModule } from './api/products/products.module';
import { TypesModule } from './api/ptypes/types.module';
import { ItemsModule } from './api/items/items.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Item } from './api/items/entity/item.entity';
import { Type } from './api/ptypes/entity/ptype.entity';
import { Global } from './api/products/entity/global.entity';

@Module({
  imports: [
    ProductsModule,
    TypesModule,
    ItemsModule,
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: '',
      password: '',
      database: 'nest_eb',
      entities: [Item, Type, Global],
      synchronize: false,
    }),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
