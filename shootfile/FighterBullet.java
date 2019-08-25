import java.awt.*;

class FighterBullet extends MovingObject{
 Image ong;
FighterBullet(){

w=h=3;//半径
dx=0;dy=-15;//速さ
hp=0;




}

void cGetImage (GameMaster ap){//画像の読み込みメソッド

ong = Toolkit.getDefaultToolkit().createImage("engimono_041.png");//画像読み込み
  }
void move(Graphics buf,int apWidth, int apHeight){
 
 if(hp>0){
    buf.drawImage(ong, x, y-20, x+40, y+60, 15, 0, 1185, 1619, null);//画像貼り付け

    if(y>0&&y<apHeight &&x>0 &&x<apWidth)
    y=y+dy;
    else hp=0;

  }
}

void revive(int x, int y){

this.x=x;
this.y=y;
hp=1;
}}

