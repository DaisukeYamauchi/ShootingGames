import java.awt.*;
import java.awt.event.*;

class Fighter extends MovingObject {
 int s=95;//x=64, y=82;    距離感把握：360-s/2, 500-s/2, 360+s/2, 500+s/2,
 int cond2=2; // 招き猫向きを管理（0:下，1:左，2:右，3:上)


Image cimg; // 招き猫の画像はこちらで管理する

boolean lflag;
boolean rflag;
boolean uflag;
boolean dflag;
boolean sflag;
int delaytime;

Fighter(int apWidth,int apHeight){



x=(int)(apWidth/2);
y=(int)(apHeight*0.8);
dx=5;
dy=5;
w=10;
h=10;
lflag=false;
rflag=false;
uflag=false;
dflag=false;
sflag=false;
delaytime=5;}

void cGetImage (GameMaster ap){//画像を取り込むメソッド

cimg = Toolkit.getDefaultToolkit().createImage("Manekineko.gif");//画像取り込み
  }
void cDraw (Graphics g){
    int imgx, imgy;//画像を読み込む位置の座標の切り替えを管理する

    imgx = cond2;
    
    imgy = cond2*s;
    g.drawImage(cimg, x, y, x+s, y+s, imgx, imgy, imgx+s, imgy+s+5, null);

  }
void revive(int apWidth,int apHeight){
}
void move(Graphics buf,int apWidth,int apHeight){//操作する向きによって画像切り替えるメソッド


if(lflag && !rflag && x>w-32){
x=x-dx;
cond2=1;}//左向きの画像に切り替え
if(rflag && !lflag && x<apWidth-w){
x=x+dx;
cond2=2;}//右向きの画像に切り替え
if(uflag && !dflag && y>h){
y=y-dy;
cond2=3;}//前(↑)向きの画像に切り替え
if(dflag && !uflag && y<apHeight-h){
y=y+dy;
cond2=0;}//後ろ(↓)向きの画像に切り替え


}
//当たり判定　条件式で複数の座標に対して判定を持たせる
boolean collisionCheck(MovingObject obj){

if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定
  this.hp--;
  obj.hp--;
  return true;
  }

else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+20) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }

 
else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+32) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }
else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+40) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }


else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+50) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }

else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+58) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }

else if(Math.abs(this.x-obj.x)<=(this.w+obj.w+64) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定の拡張
  this.hp--;
  obj.hp--;
  return true;
  }
 else
  return false;
  }


}

