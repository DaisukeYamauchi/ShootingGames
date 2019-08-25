import java.awt.*;
class Luckyitem extends MovingObject{//敵Bクラス
Image li;//画像取り込む場所


Luckyitem(int apWidth, int apHeight){//コンストラクタ
super(apWidth,apHeight);

w=12;//半径
h=12;//半径
hp=0;

}
void cGetImage (GameMaster ap){//画像を取り込むメソッド

li = Toolkit.getDefaultToolkit().createImage("samu_tool_01_d_r4_c92.png");//画像取り込み
  }
void move(Graphics buf, int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド



if(hp>0){
buf.drawImage(li, x, y, x+60, y+60, 0, 0, 361, 278, null);//画像貼り付け


x=x+dx;//更新
y=y+dy;
if(y>1000)//画面外で
hp=0;//消滅
}}

void revive(int apWidth, int apHeight){
x=(int)(Math.random()*(apWidth-2*w)+w);//ランダムな場所に生成
y=(int)(Math.random()*100+10);;
dy=0;
dx=0;

hp=1;
}

 boolean collisionCheck(MovingObject obj){//オーバーライド

if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//あたり判定
  this.hp--;
  obj.hp+=5;
 
  return true;
  }
  else
  return false;
  }

}