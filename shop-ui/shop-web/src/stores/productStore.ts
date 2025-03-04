import { defineStore } from "pinia";
import { ref } from "vue";
import type { Product } from "@/types/Product";

export const useProductStore = defineStore("product", () => {

  const products = ref<Product[]>([])
  const productsLoading = ref(false)

  const getProducts = async () => {

    const response = await fetch('/products.json')
    const returnedProducts: Product[] = await response.json()
    products.value = returnedProducts
    return returnedProducts
  }



  return { products, getProducts, productsLoading };
});
