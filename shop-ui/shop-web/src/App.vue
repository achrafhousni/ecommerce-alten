<script setup lang="ts">
import { RouterView } from 'vue-router'
import SideBar from './components/sidebar/SideBar.vue';
import Header from './components/header/Header.vue';
 
 </script>

<template>
  <Header></Header>
    <div class="flex">
     <div class="w-1/2">
        <SideBar></SideBar>
      </div>
      
       
      <div class="w-full">
       <RouterView></RouterView>
       </div>
     </div>  



</template>

 
