import java.awt.*;
class EnemyA extends MovingObject{//敵Aクラス

Image cp1;//画像を入れる場所

EnemyA(int apWidth, int apHeight){//コンストラクタ
super(apWidth,apHeight);

w=12;//半径
h=12;//半径
hp=0;


}
void cGetImage (GameMaster ap){//オブジェクトの画像を読み込むメソッド

cp1 = Toolkit.getDefaultToolkit().createImage("122639m.png");//画像読み込み
  }
void move(Graphics buf, int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド




if(hp>0){
buf.drawImage(cp1, x, y, x+60, y+90, 43, 0, 172, 220, null);//画像貼り付け

x=x+dx;//更新
y=y+dy;
if(y>apHeight+h)hp=0;//画面外で消滅
if(x>apWidth+w)hp=0;
if(x<5)hp=0;
}
}

void revive(int apWidth, int apHeight){
x=(int)(Math.random()*apWidth);//ランダムな場所に生成
y=(int)(Math.random()*100+10);

dy=0;

if((x%2)==0) dx=(int)(Math.random()*5+1);//右方向に動く
else if((x%2)==1) dx=-(int)(Math.random()*5+1); //左方向に動く

hp=1;
}

  boolean collisionCheck(MovingObject obj,int[] cPoint){//オーバーロード

if(Math.abs(this.x-obj.x)<=(this.w+obj.w+30) && Math.abs(this.y-obj.y+90)<=(this.h+obj.h)){//当たり判定
  this.hp--;
  obj.hp--;
  cPoint[0]++;//shootpointに加算
  return true;
  }
  else
  return false;
  }


}


