import java.awt.*;
class EnemyB extends MovingObject{//敵Bクラス
Image cp2;//画像取り込む場所


EnemyB(int apWidth, int apHeight){//コンストラクタ
super(apWidth,apHeight);

w=12;//半径
h=12;//半径
hp=0;

}
void cGetImage (GameMaster ap){//画像を取り込むメソッド

cp2 = Toolkit.getDefaultToolkit().createImage("122651m.png");//画像取り込み
  }
void move(Graphics buf, int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド



if(hp>0){
buf.drawImage(cp2, x, y, x+60, y+90, 55, 5, 170, 210, null);//画像貼り付け


x=x+dx;//更新
y=y+dy;
if(y>apHeight+h)//画面外で
hp=0;//消滅
}}

void revive(int apWidth, int apHeight){
x=(int)(Math.random()*(apWidth-2*w)+w);//ランダムな場所に生成
y=(int)(Math.random()*100+10);;
dy=5;
dx=0;

hp=1;
}

 boolean collisionCheck(MovingObject obj,int[] cPoint){//オーバーライド

if(Math.abs(this.x-obj.x)<=(this.w+obj.w+30) && Math.abs(this.y-obj.y+90)<=(this.h+obj.h)){//あたり判定
  this.hp--;
  obj.hp--;
  cPoint[0]++;//shootpointを加算する
  return true;
  }
  else
  return false;
  }

}



