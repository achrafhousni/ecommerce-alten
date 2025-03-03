import { defineStore } from "pinia";
import { ref, computed } from "vue";
import type { Product } from "@/types/Product";
import type { CartItem } from "@/types/CartItem";

export const useCartStore = defineStore("cart", () => {
    const items = ref<CartItem[]>([]);

    const addProduct = (product: Product) => {
        const existingItem = items.value.find((item) => item.product.id === product.id);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            items.value.push({ product, quantity: 1 });
        }
    };

    const removeProduct = (productId: number) => {
        console.log("product ", productId);
        items.value = items.value.filter((item) => item.product.id !== productId);
    };

    const totalItems = computed(() => items.value.reduce((sum, item) => sum + item.quantity, 0));
    const totalPrice = computed(() => items.value.reduce((sum, item) => sum + item.product.price * item.quantity, 0));

    return { items, addProduct, removeProduct, totalItems, totalPrice };
});
