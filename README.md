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