import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import AccountView from "../views/AccountView.vue";
import TransferView from "../views/TransferView.vue";
import TransactionHistoryView from "../views/TransactionHistoryView.vue";
import ReferralBonusView from "@/views/ReferralBonusView.vue";
import HanabankView from "@/views/HanabankView.vue";
import AdminView from "@/views/AdminView.vue";
import UsersView from "@/views/UserListView.vue";
import UserListView from "@/views/UserListView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "hanabank",
      component: HanabankView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
    },
    {
      path: "/account",
      name: "account",
      component: AccountView,
    },
    {
      path: "/transfer",
      name: "transfer",
      component: TransferView,
    },
    {
      path: "/history",
      name: "history",
      component: TransactionHistoryView,
    },
    {
      path: "/referral-bonus",
      name: "ReferralBonus",
      component: ReferralBonusView,
    },
    {
      path: "/admin",
      name: "admin",
      component: AdminView,
    },
    {
      path: "/userlist",
      name: "userlist",
      component: UserListView,
    },
  ],
});

export default router;