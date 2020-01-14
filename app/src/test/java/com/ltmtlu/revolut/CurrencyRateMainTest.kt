package com.ltmtlu.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ltmtlu.revolut.data.repository.CurrencyRateRepository
import com.ltmtlu.revolut.ui.viewmodel.CurrencyRateViewModel
import com.ltmtlu.revolut.ui.viewmodel.RateModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class CurrencyRateMainTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    lateinit var currencyRateViewModel: CurrencyRateViewModel
    @Mock
    lateinit var currencyRateRepository: CurrencyRateRepository
    lateinit var currencyRateMap: Map<String, Float>
    val baseCurrency = "EUR"
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var rateModel: RateModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        currencyRateRepository = mock(CurrencyRateRepository::class.java)
        currencyRateViewModel = CurrencyRateViewModel(currencyRateRepository)
        currencyRateMap = mapOf("AUD" to 1.6f, "BGN" to 0.6f)
        rateModel = RateModel("AUD", 1.6f)
    }

    @Test
    fun testAmountRateCalculation() {
        val amount = 2f
        val amountRate = rateModel.getAmountRate(amount)
        Assert.assertEquals(amountRate, amount * rateModel.rate)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testVerifyCurrencyRateAPI() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            `when`(currencyRateRepository.fetchCurrencyRate(baseCurrency)).thenReturn(
                currencyRateMap
            )

            currencyRateViewModel.getCurrencyRate()
            Mockito.verify(currencyRateRepository).fetchCurrencyRate(baseCurrency)
        }


    @Test
    fun testMapCurrenciesToList() {
        var list = currencyRateViewModel.mapCurrencies(currencyRateMap)
        Assert.assertEquals(list, createCurrencyList())
    }

    private fun createCurrencyList(): ArrayList<RateModel> {
        var currencyList = ArrayList<RateModel>()
        currencyList.add(RateModel("EUR", 1f))
        currencyList.add(RateModel("AUD", 1.6f))
        currencyList.add(RateModel("BGN", 0.6f))
        return currencyList
    }

    @After
    fun tearDown() {
        mainThreadSurrogate.close()
    }
}
