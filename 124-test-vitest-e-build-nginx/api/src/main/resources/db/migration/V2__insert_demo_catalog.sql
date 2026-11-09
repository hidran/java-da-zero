-- Fictitious demo catalog: 5 categories, 30 products (prices in cents).
INSERT INTO category (name) VALUES ('Peripherals');
INSERT INTO category (name) VALUES ('Displays');
INSERT INTO category (name) VALUES ('Audio');
INSERT INTO category (name) VALUES ('Desk');
INSERT INTO category (name) VALUES ('Cables and power');

-- The first twelve are the products of lesson 58, with the same ids.
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Keyboard', 'Compact keyboard with quiet keys and a detachable cable.', 4900, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Mouse', 'Wireless mouse with a silent click and a two-year battery.', 2500, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Monitor', '27 inch monitor with a sharp picture and thin borders.', 18900, 2);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Webcam', 'Full HD webcam with a privacy cover and two microphones.', 5900, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Headset', 'Over-ear headset with a folding microphone for calls.', 7900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Laptop stand', 'Aluminium stand that lifts the laptop to eye level.', 3900, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('USB hub', 'Four-port USB hub that fits in a pocket.', 2900, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Desk lamp', 'LED desk lamp with three colour temperatures.', 3400, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Microphone', 'USB microphone for podcasts and video lessons.', 9900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Speakers', 'Pair of desk speakers with a volume knob on the front.', 6900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Mouse pad', 'Large cloth mouse pad with stitched edges.', 1500, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Docking station', 'One cable for screen, network, charging and USB.', 15900, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Drawing tablet', 'Pen tablet for sketches and handwritten notes.', 8900, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Numeric keypad', 'Wireless keypad for spreadsheets and accounting.', 1900, 1);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Ultrawide monitor', '34 inch curved monitor for two windows side by side.', 39900, 2);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Portable monitor', '15 inch monitor powered by a single USB-C cable.', 14900, 2);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Monitor arm', 'Gas spring arm that frees the desk under the screen.', 6900, 2);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Screen light bar', 'Light bar that sits on the monitor and lights the desk.', 4500, 2);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Earbuds', 'Wireless earbuds with a charging case.', 5900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Headphone stand', 'Wooden stand that keeps the headset off the desk.', 1900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Sound bar', 'Slim sound bar that fits under the monitor.', 12900, 3);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Standing desk', 'Electric desk that moves from sitting to standing.', 34900, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Office chair', 'Ergonomic chair with lumbar support and armrests.', 24900, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Cable tray', 'Tray under the desk that hides cables and plugs.', 1900, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Footrest', 'Tilting footrest for a better sitting posture.', 2900, 4);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('USB-C charger', '65 watt charger for a laptop and a phone at once.', 3500, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Power bank', '20000 mAh power bank with two USB-C ports.', 3900, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Extension lead', 'Four sockets and two USB ports on a two-metre cable.', 2200, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('HDMI cable', 'Two-metre HDMI cable for 4K screens.', 1200, 5);
INSERT INTO product (name, description, price_in_cents, category_id) VALUES
    ('Desk mat', 'Felt desk mat that covers keyboard and mouse area.', 2400, 4);
