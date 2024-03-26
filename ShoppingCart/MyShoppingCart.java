package homework.ShoppingCart;
import java.util.*;

class Product {
    private String key;
    private String name;
    private double price;

    public Product(String key, String name, double price) {
        this.key = key;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(key, product.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public double getPrice() {
        return price;
    }
}

class ShoppingCart {
    private HashMap<Product, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    private double total = 0;
    public void showItems() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println("상품: " + entry.getKey().getName() + ", 수량: " + entry.getValue());
            total += entry.getKey().getPrice() * entry.getValue();
        }
        System.out.printf("총합: %.0f", total );
    }

    public void addProduct(Product product, int quantity) {
        if (items.containsKey(product)) {
            items.put(product, items.get(product) + quantity);
        } else {
            items.put(product, quantity);
        }
        System.out.println(quantity + "개의 " + product.getName() + "을/를 장바구니에 추가했습니다.");
    }

    public void removeProduct(Product product, int quantity) {
        if (items.containsKey(product)) {
            int currentQuantity = items.get(product);
            if (currentQuantity > quantity) {
                items.put(product, currentQuantity - quantity);
                System.out.println(quantity + "개의 " + product.getName() + "을/를 장바구니에서 제거했습니다.");
            } else if (currentQuantity == quantity) {
                items.remove(product);
                System.out.println(product.getName() + "이/가 장바구니에서 모두 제거되었습니다.");
            } else {
                System.out.println("장바구니에 해당 상품의 충분한 수량이 없습니다.");
            }
        } else {
            System.out.println("장바구니에 해당 상품이 존재하지 않습니다.");
        }
    }
}


public class MyShoppingCart {
    public static void addToCart(Scanner scanner, HashSet<Product> productList, ShoppingCart cart) {
        while (true) {
            System.out.println("장바구니에 담을 상품의 Key를 입력하세요 (종료하려면 'exit' 입력): ");
            String key = scanner.next();
            if (key.equals("exit")) break;
            Product selectedProduct = null;
            for (Product product : productList) {
                if (product.getKey().equals(key)) {
                    selectedProduct = product;
                    break;
                }
            }
            if (selectedProduct == null) {
                System.out.println("해당 Key를 가진 상품이 존재하지 않습니다.");
                continue;
            }
            System.out.println("상품의 수량을 입력하세요: ");
            int quantity = scanner.nextInt();
            cart.addProduct(selectedProduct, quantity);
        }
    }

    public static void removeFromCart(Scanner scanner, HashSet<Product> productList, ShoppingCart cart) {
        while (true) {
            System.out.println("장바구니에서 제거할 상품의 Key를 입력하세요 (종료하려면 'exit' 입력): ");
            String key = scanner.next();
            if (key.equals("exit")) break;
            Product selectedProduct = null;
            for (Product product : productList) {
                if (product.getKey().equals(key)) {
                    selectedProduct = product;
                    break;
                }
            }
            if (selectedProduct == null) {
                System.out.println("해당 Key를 가진 상품이 존재하지 않습니다.");
                continue;
            }
            System.out.println("상품의 수량을 입력하세요: ");
            int quantity = scanner.nextInt();
            cart.removeProduct(selectedProduct, quantity);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<Product> productList = new HashSet<>();
        ShoppingCart cart = new ShoppingCart();

        // 상품 목록 구성
        while (true) {
            System.out.println("상품의 Key, 이름, 가격을 입력하세요 (종료하려면 'exit' 입력): ");
            String key = scanner.next();
            if (key.equals("exit")) break;
            String name = scanner.next();
            double price = scanner.nextDouble();
            Product product = new Product(key, name, price);
            productList.add(product);
            for (Product products : productList) {
                System.out.printf("key : %s, 이름 : %s, 가격 : %.0f %n", products.getKey(), products.getName(), products.getPrice());
                // Product 클래스에 적절한 toString() 메서드가 구현되어 있다면 해당 메서드가 호출됩니다.
            }
        }
        label:
        while (true) {
            System.out.println("원하는 동작을 입력하세요. (종료하려면 'exit' 입력): ");
            System.out.println("장바구니 추가: add , 장바구니 제거 : remove");
            String key = scanner.next();
            switch (key) {
                case "exit":
                    break label;
                case "add":
                    // 장바구니에 상품 추가
                    addToCart(scanner, productList, cart);
                    break;
                case "remove":
                    // 장바구니에서 상품 제거
                    removeFromCart(scanner, productList, cart);
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }

        // 장바구니 출력
        System.out.println("장바구니에 담겨있는 상품들:");
        cart.showItems();

        scanner.close();
    }
}
