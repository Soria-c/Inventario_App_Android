import { Controller, Get, HttpCode, Param, Req } from '@nestjs/common';
import { Request } from 'express';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(@Param('aea') p: string): string {
    console.log(p);

    return this.appService.getHello();
  }
}
