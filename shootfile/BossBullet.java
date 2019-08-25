import java.awt.*;

class BossBullet extends MovingObject{//Bossクラスの球
Image hinotama;//画像を入れる場所
BossBullet(){//コンストラクタ

w=h=7;//半径
dx=0;dy=-5;//速さ
hp=0;
}
void cGetImage (GameMaster ap){//オブジェクトの画像を読み込むメソッド

hinotama = Toolkit.getDefaultToolkit().createImage("images.png");//画像の読み込み
  }
void move(Graphics buf,int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド

if(hp>0){
buf.drawImage(hinotama, x, y, x+60, y+90, 0, 0, 85, 163, null);//画像貼り付け

if(y>0&&y<apHeight &&x>0 &&x<apWidth)//もし画面内なら
y=y-dy;//更新
else hp=0;

}
}

void revive(int x, int y){
this.x=x;
this.y=y;
hp=1;
}}

