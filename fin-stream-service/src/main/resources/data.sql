INSERT INTO users (username, balance) VALUES ('trader_pro', 100000.00) ON CONFLICT DO NOTHING;
INSERT INTO stocks (stock_name, symbol, base_price) VALUES
('Zomato Ltd', 'ZOMATO', 158.45),
('Reliance Industries', 'RELIANCE', 2950.10),
('Tata Motors', 'TATAMOTORS', 980.50) ON CONFLICT DO NOTHING;