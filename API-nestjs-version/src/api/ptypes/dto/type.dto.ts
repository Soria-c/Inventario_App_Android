import { IsAlpha, IsNotEmpty } from 'class-validator';

export class TypeDTO {
  @IsAlpha()
  @IsNotEmpty()
  name: string;
}
