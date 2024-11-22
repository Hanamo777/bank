<template>
  <div class="main-container">
    <Header></Header>
    <main>
      <div class="exchange-info">
        <h3>{{ $t('exchange.title') }}</h3>
        <div v-if="loading">{{ $t('exchange.loading') }}</div>
        <div v-else-if="error">{{ error }}</div>
        <div v-else>
          <table class="exchange-table">
            <thead>
              <tr>
                <th>{{ $t('exchange.table.currency') }}</th>
                <th>{{ $t('exchange.table.buyRate') }}</th>
                <th>{{ $t('exchange.table.buySpread') }}</th>
                <th>{{ $t('exchange.table.sellRate') }}</th>
                <th>{{ $t('exchange.table.sellSpread') }}</th>
                <th>{{ $t('exchange.table.baseRate') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(spread, currency) in spreads" :key="currency">
                <td class="exchange-currency">{{ currency }}</td>
                <td>
                  {{
                    calculateBuyRate(
                      currency,
                      (rates['KRW'] / rates[currency]) *
                        (currency === 'JPY' || currency === 'VND' ? 100 : 1)
                    ).toFixed(2)
                  }}
                </td>
                <td>{{ spread.buy.toFixed(2) }}</td>
                <td>
                  {{
                    calculateSellRate(
                      currency,
                      (rates['KRW'] / rates[currency]) *
                        (currency === 'JPY' || currency === 'VND' ? 100 : 1)
                    ).toFixed(2)
                  }}
                </td>
                <td>{{ spread.sell.toFixed(2) }}</td>
                <td>
                  {{
                    (
                      (rates['KRW'] / rates[currency]) *
                      (currency === 'JPY' || currency === 'VND' ? 100 : 1)
                    ).toFixed(2)
                  }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import Header from '@/components/Header.vue';
import { useI18n } from 'vue-i18n';

export default {
  name: 'ExchangeRateView',
  components: {
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      rates: {},
      loading: true,
      error: null,
      spreads: {
        USD: { buy: 1.75, sell: 1.75 },
        JPY: { buy: 1.75, sell: 1.75 },
        EUR: { buy: 1.99, sell: 1.99 },
        CNY: { buy: 5.0, sell: 5.0 },
        HKD: { buy: 1.97, sell: 1.97 },
        THB: { buy: 5.0, sell: 6.0 },
        TWD: { buy: 13.1, sell: 10.0 },
        PHP: { buy: 10.0, sell: 8.2 },
        SGD: { buy: 1.99, sell: 1.99 },
        AUD: { buy: 1.97, sell: 1.97 },
        VND: { buy: 11.8, sell: 11.8 },
        GBP: { buy: 1.97, sell: 1.97 },
        CAD: { buy: 1.97, sell: 1.97 },
        MYR: { buy: 7.4, sell: 7.4 },
        RUB: { buy: 9.5, sell: 17.5 },
        ZAR: { buy: 8.0, sell: 8.0 },
        NOK: { buy: 3.3, sell: 5.7 },
        NZD: { buy: 1.97, sell: 1.97 },
        DKK: { buy: 3.3, sell: 5.7 },
        MXN: { buy: 10.5, sell: 10.5 },
      },
    };
  },
  created() {
    this.fetchExchangeRates();
  },
  methods: {
    async fetchExchangeRates() {
      try {
        const response = await axios.get(
          'https://v6.exchangerate-api.com/v6/b4d93b67845f9c2c19fa3ab2/latest/USD'
        );
        this.rates = response.data.conversion_rates;
      } catch (error) {
        this.error = this.$t('exchange.error');
      } finally {
        this.loading = false;
      }
    },
    calculateBuyRate(currency, baseRate) {
      const spread = this.spreads[currency]?.buy || 0;
      return baseRate * ((100 + spread) / 100);
    },
    calculateSellRate(currency, baseRate) {
      const spread = this.spreads[currency]?.sell || 0;
      return baseRate * ((100 - spread) / 100);
    },
  },
};
</script>

<style scoped>
.exchange-info {
  background-color: #e8f5e9;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  text-align: center;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
    'Helvetica Neue', Arial, sans-serif;
  margin: 2rem auto;
}

.exchange-info h3 {
  font-weight: 500;
  margin-bottom: 1rem;
  color: #2e7d32; /* 진한 초록색 */
  text-align: center;
}

.exchange-table {
  width: 100%; /* 표 너비를 전체 너비로 설정 */
  border-collapse: collapse;
  margin: 0 auto;
  text-align: center;
}

.exchange-table th,
.exchange-table td {
  border: 1px solid #ccc;
  padding: 1rem; /* 각 셀에 여유 공간을 줘서 가독성 향상 */
  font-size: 1rem;
}

.exchange-table th {
  background-color: #b8b8b8; /* 중간 밝기의 초록색 */
  color: white; /* 진한 초록색 */
  font-weight: bold;
}

.exchange-currency {
  text-align: center;
}
.exchange-table td {
  background-color: #f5f5f5; /* 연한 회색 */
  color: #2e7d32;
  text-align: end;
}

.rate {
  color: #388e3c; /* 진한 초록색 강조 */
  font-weight: bold;
}
</style>
