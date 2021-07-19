# spring-demo

## Component

springでは以下の処理を行う。

1. コンポーネントスキャン
2. コンポーネント内の@Autowiredのついた箇所にインスタンスを注入する

### コンポーネントスキャン

以下アノテーションがついたクラスを探し、DIコンテナに登録する。  

> DIコンテナ：クラスの管理をする入れ物  
> 注入するコンポーネントのスコープも  
> ```@Scope("singleton")```のようなアノテーションをクラスにつけて管理できる  
> デフォルトのスコープはsingletonなので、状態をクラス属性にいれてはいけない

- @Component
- @Controller
- @Service
- @Repository
- @Configuration
- @RestController
- @ControllerAdvice
- @ManagedBean
- @Named
- @Mapper
- @Bean

> Controllerクラスのメソッド引数は、ModelやLocaleなどのクラスを指定することで  
> Webページから送られてくるデータを自動で取り込む

### コンポーネント内の@Autowiredのついた箇所にインスタンスを注入する

1. フィールドインジェクション

```java
@Component
public class Sample {
    @Autowired
    private SampleComponent component;
}
```

2. コンストラクタインジェクション

```java
@Component
public class Sample {
    private SampleComponent component;

    // コンストラクタインジェクションでは@Autowiredを省略できる
    public Sample(SampleComponent component) {
        this.component = component;
    }
}
```

setterインジェクションもあるが、省略。  
Springが推奨しているのはコンストラクタインジェクション。

## プロジェクト内で実装したもの

- Controller
- Service
- Repository
- バインド&バリデーション
- JPA: Entity
- JPA: テーブル結合
- トランザクション
- SpringSecurity: ログイン
- SpringSecurity: 認可
- AOP
- エラー処理
- REST API

## H2-console

h2コンソール画面: http://localhost:8080/h2-console

- ドライバクラス: org.h2.Driver
- jdbcURL: jdbc:h2:mem:testdb
- ユーザ名: sa
- パスワード: なし