import { useState, useEffect } from 'react'
import Head from 'next/head'

export default function Home() {
  const [phones, setPhones] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchQuery, setSearchQuery] = useState('')
  const [selectedBrand, setSelectedBrand] = useState('')
  const [selectedType, setSelectedType] = useState('')

  useEffect(() => {
    fetchPhones()
  }, [])

  const fetchPhones = async () => {
    try {
      const response = await fetch('/api/v1/phones?page=0&size=20')
      const data = await response.json()
      if (data.success) {
        setPhones(data.data.content || data.data)
      }
    } catch (error) {
      console.error('Error fetching phones:', error)
    } finally {
      setLoading(false)
    }
  }

  const searchPhones = async () => {
    if (!searchQuery.trim()) {
      fetchPhones()
      return
    }
    
    try {
      setLoading(true)
      const response = await fetch(`/api/v1/phones/search?query=${encodeURIComponent(searchQuery)}`)
      const data = await response.json()
      if (data.success) {
        setPhones(data.data)
      }
    } catch (error) {
      console.error('Error searching phones:', error)
    } finally {
      setLoading(false)
    }
  }

  const filterByBrand = async (brand) => {
    try {
      setLoading(true)
      const response = await fetch(`/api/v1/phones/brand/${encodeURIComponent(brand)}`)
      const data = await response.json()
      if (data.success) {
        setPhones(data.data)
      }
    } catch (error) {
      console.error('Error filtering by brand:', error)
    } finally {
      setLoading(false)
    }
  }

  const filterByType = async (type) => {
    try {
      setLoading(true)
      const response = await fetch(`/api/v1/phones/type/${encodeURIComponent(type)}`)
      const data = await response.json()
      if (data.success) {
        setPhones(data.data)
      }
    } catch (error) {
      console.error('Error filtering by type:', error)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <Head>
        <title>Phone Suggester - Find Your Perfect Phone</title>
        <meta name="description" content="Modern phone suggestion application" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="text-center mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">
            📱 Phone Suggester
          </h1>
          <p className="text-xl text-gray-600">
            Find your perfect phone with our intelligent suggestions
          </p>
        </div>

        {/* Search and Filters */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            {/* Search */}
            <div className="md:col-span-2">
              <input
                type="text"
                placeholder="Search phones..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
            <button
              onClick={searchPhones}
              className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
            >
              Search
            </button>
            <button
              onClick={fetchPhones}
              className="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors"
            >
              Reset
            </button>
          </div>

          {/* Quick Filters */}
          <div className="mt-4 flex flex-wrap gap-2">
            <button
              onClick={() => filterByBrand('Apple')}
              className="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
            >
              Apple
            </button>
            <button
              onClick={() => filterByBrand('Samsung')}
              className="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
            >
              Samsung
            </button>
            <button
              onClick={() => filterByType('Gaming')}
              className="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
            >
              Gaming
            </button>
            <button
              onClick={() => filterByType('Photography')}
              className="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
            >
              Photography
            </button>
          </div>
        </div>

        {/* Phone Grid */}
        {loading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
            <p className="mt-4 text-gray-600">Loading phones...</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {phones.map((phone) => (
              <div key={phone.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
                <div className="p-6">
                  <div className="flex items-center justify-between mb-4">
                    <h3 className="text-xl font-semibold text-gray-900">{phone.name}</h3>
                    <span className="text-sm text-gray-500">{phone.brand}</span>
                  </div>
                  
                  <p className="text-gray-600 mb-4 line-clamp-2">{phone.description}</p>
                  
                  <div className="flex items-center justify-between mb-4">
                    <span className="text-2xl font-bold text-blue-600">
                      ${phone.price}
                    </span>
                    <div className="flex items-center">
                      <span className="text-yellow-500">★</span>
                      <span className="ml-1 text-sm text-gray-600">{phone.rating}</span>
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-2 text-sm text-gray-500 mb-4">
                    <div>Storage: {phone.storageGB}GB</div>
                    <div>RAM: {phone.ramGB}GB</div>
                    <div>Battery: {phone.batteryCapacity}mAh</div>
                    <div>Screen: {phone.screenSize}"</div>
                  </div>

                  <div className="flex flex-wrap gap-1">
                    {phone.usageTypes.map((type, index) => (
                      <span
                        key={index}
                        className="px-2 py-1 bg-blue-100 text-blue-800 text-xs rounded-full"
                      >
                        {type}
                      </span>
                    ))}
                  </div>

                  <div className="mt-4 flex flex-wrap gap-2">
                    {phone.is5G && (
                      <span className="px-2 py-1 bg-green-100 text-green-800 text-xs rounded-full">
                        5G
                      </span>
                    )}
                    {phone.isWaterResistant && (
                      <span className="px-2 py-1 bg-blue-100 text-blue-800 text-xs rounded-full">
                        Water Resistant
                      </span>
                    )}
                    {phone.hasWirelessCharging && (
                      <span className="px-2 py-1 bg-purple-100 text-purple-800 text-xs rounded-full">
                        Wireless Charging
                      </span>
                    )}
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}

        {!loading && phones.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-600">No phones found. Try adjusting your search criteria.</p>
          </div>
        )}
      </main>
    </div>
  )
} 